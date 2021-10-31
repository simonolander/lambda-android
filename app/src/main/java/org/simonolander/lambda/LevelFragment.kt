package org.simonolander.lambda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class LevelFragment : Fragment() {

    private lateinit var viewModel: TutorialViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.level, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TutorialViewModel::class.java]
        viewModel.getPage().observe(viewLifecycleOwner) {}
        findNavController()
            .navigate(R.id.c1L1Fragment)
    }
}
