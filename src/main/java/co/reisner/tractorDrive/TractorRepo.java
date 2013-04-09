package co.reisner.tractorDrive;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.*;


public class TractorRepo {

    private Repository repo;
    private Git git;
    private string version;
    
    public TractorRepo(String path) {
	repo = new FileRepository(path + "/.git");
	git = new Git(repo);
    }
    
    public void updateRelease(ProgressMonitor monitor) {
	git.checkout().setName("release").call();
	git.pull().setProgressMonitor(monitor).call();
	git.checkout().setName("master").call();
    }

    public string getNewReleaseNotes() {


    }
}
