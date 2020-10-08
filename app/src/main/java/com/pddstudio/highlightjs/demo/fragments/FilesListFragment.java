package com.pddstudio.highlightjs.demo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pddstudio.highlightjs.demo.R;
import com.pddstudio.highlightjs.demo.SyntaxActivity;
import com.pddstudio.highlightjs.demo.adapters.FilesAdapter;
import com.pddstudio.highlightjs.demo.utils.FileObject;
import com.pddstudio.highlightjs.demo.utils.RepositoryLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 13.06.16. For more Details and Licensing
 * have a look at the README.md
 */

public class FilesListFragment extends Fragment implements RepositoryLoader.Callback, FilesAdapter.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private FilesAdapter filesAdapter;

    public static FilesListFragment newInstance() {
        return new FilesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_file_list, container, false);
        recyclerView = root.findViewById(R.id.files_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        filesAdapter = new FilesAdapter(new LinkedList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(filesAdapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RepositoryLoader.get().loadFiles(this);
    }

    @Override
    public void onItemLoaded(FileObject fileObject) {
        filesAdapter.addItem(fileObject);
    }

    @Override
    public void onFilesLoaded(List<FileObject> fileObjects) {}

    @Override
    public void onItemSelected(int position) {
        Log.d(getClass().getSimpleName(), "Position: " + position + " URL: " + filesAdapter.getItem(position).getUrl().toString());
        Intent i = new Intent(getContext(), SyntaxActivity.class);
        i.putExtra("fileObject", filesAdapter.getItem(position));
        startActivity(i);
    }

}
