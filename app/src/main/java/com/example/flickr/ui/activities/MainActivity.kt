package com.example.flickr.ui.activities

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import com.example.flickr.R
import com.example.flickr.databinding.ActivityMainBinding
import com.example.flickr.ui.extensions.addGridItemDecoration
import com.example.flickr.viewModels.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val tagsAdapter by lazy {
        TagsAdapter {
            viewModel.removeTag(it)
        }
    }
    private val photosAdapter by lazy { PhotosAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.changeOwnerId(newText)
                return false
            }
        })

        binding.addTag.setOnClickListener {
            showAddTagAlert()
        }

        //userViewModel.fetchUser()
        binding.chipRecyclerView.adapter = tagsAdapter
        binding.chipRecyclerView2.adapter = tagsAdapter
        binding.recyclerView.apply {
            addGridItemDecoration()
            adapter = photosAdapter
        }
        observeViewState()
    }

    private fun observeViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.photosUIState.observe(this@MainActivity) {
                render(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.photosRequestModel.observe(this@MainActivity) {
                viewModel.fetchPhotos(it)
                runOnUiThread {
                    tagsAdapter.submitList(it.tags)
                }
            }
        }
    }

    private fun showAddTagAlert() {
        val textInputLayout = TextInputLayout(this)
        val input = EditText(this).apply {
            //textInputLayout.hint = getString(R.string.alert_hint_add_tag)
        }.also {
            textInputLayout.addView(it)
        }

        MaterialAlertDialogBuilder(this)
            .setCancelable(true)
            .setTitle(R.string.alert_title_add_tag)
            .setView(textInputLayout)
            //.setMessage("")
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.addTag(input.text.toString())
                dialog.cancel()
            }
            /*.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }*/
            .create().also {
                it.show()
                input.postDelayed({
                    input.dispatchTouchEvent(
                        MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN,
                            0f,
                            0f,
                            0
                        )
                    )
                    input.dispatchTouchEvent(
                        MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP,
                            0f,
                            0f,
                            0
                        )
                    )
                }, 200)
                //https://stackoverflow.com/a/7784904


                //val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                //inputMethodManager.showSoftInput(input, 0)
                //https://stackoverflow.com/questions/5105354/how-to-show-soft-keyboard-when-edittext-is-focused
            }
    }

    private fun render(viewState: ViewModel.PhotosUIState) {
        when (viewState) {
            is ViewModel.PhotosUIState.Loading -> {
                //showLoading()

                Log.e("render", "Loading")
            }
            is ViewModel.PhotosUIState.Error -> {
                //hildeLoading()
                //showMessage(viewState.error)
                Log.e("render", "Error")
            }
            is ViewModel.PhotosUIState.Data -> {
                Log.e("render", "Data ${viewState.tasks}")
                //hildeLoading()
                photosAdapter.submitData(lifecycle, viewState.tasks)
            }
        }
    }
}