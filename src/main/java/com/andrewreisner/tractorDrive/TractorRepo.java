package com.andrewreisner.tractorDrive;

import java.io.IOException;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepository;


public class TractorRepo {

    private Repository repo;
    private Git git;
    private String version;
    
    public TractorRepo(String path) throws IOException {
	repo = new FileRepository(path + "/.git");
	git = new Git(repo);
    }
    
    public void updateRelease(ProgressMonitor monitor) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
	git.checkout().setName("release").call();
	git.pull().setProgressMonitor(monitor).call();
	git.checkout().setName("master").call();
    }

    public String getNewReleaseNotes() {
    	return "";

    }
}
