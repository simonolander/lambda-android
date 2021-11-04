package org.simonolander.lambda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.simonolander.lambda.model.LevelId
import org.simonolander.lambda.model.chapters
import org.simonolander.lambda.room.LambdaDatabase

class LevelFragment : Fragment() {

    private lateinit var viewModel: TutorialViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.level, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TutorialViewModel::class.java]
        viewModel.getPage().observe(viewLifecycleOwner) {}
        LambdaDatabase.getInstance(requireContext())
            .levelCompletionDao()
            .getAll()
            .observe(viewLifecycleOwner) { levelCompletions ->
                val completedLevelIds = levelCompletions.map { LevelId(it.levelId) }.toSet()
                val firstUncompletedLevel = chapters.flatMap { it.levels }
                    .firstOrNull { it.id !in completedLevelIds }
                findNavController()
                    .navigate(firstUncompletedLevel?.destination ?: R.id.c1L2Fragment)
            }
    }
}
