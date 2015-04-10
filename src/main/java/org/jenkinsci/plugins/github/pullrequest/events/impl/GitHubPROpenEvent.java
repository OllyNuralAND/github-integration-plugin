package org.jenkinsci.plugins.github.pullrequest.events.impl;

import com.google.common.collect.Iterables;
import hudson.Extension;
import org.jenkinsci.plugins.github.pullrequest.GitHubPRCause;
import org.jenkinsci.plugins.github.pullrequest.GitHubPRPullRequest;
import org.jenkinsci.plugins.github.pullrequest.GitHubPRTrigger;
import org.jenkinsci.plugins.github.pullrequest.events.GitHubPREvent;
import org.jenkinsci.plugins.github.pullrequest.events.GitHubPREventDescriptor;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestCommitDetail;
import org.kohsuke.github.GitUser;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * When PR opened or commits changed in it
 *
 * @author Kanstantsin Shautsou
 */
public class GitHubPROpenEvent extends GitHubPREvent {
    private static final Logger LOGGER = Logger.getLogger(GitHubPROpenEvent.class.getName());

    @DataBoundConstructor
    public GitHubPROpenEvent() {
    }

    @Override
    public GitHubPRCause isStateChanged(GitHubPRTrigger gitHubPRTrigger, GHPullRequest remotePR, GitHubPRPullRequest localPR) throws IOException {
        if (remotePR.getState().equals(GHIssueState.CLOSED)) {
            return null; // already closed, nothing to check
        }

        GitHubPRCause cause = null;
        String causeMessage = "PR opened";
        if (localPR == null) { // new
            cause = new GitHubPRCause(remotePR, remotePR.getUser(), causeMessage, null, null);
        }

        return cause;
    }

    @Extension
    public static class DescriptorImpl extends GitHubPREventDescriptor {
        @Override
        public final String getDisplayName() {
            return "Pull Request Opened";
        }
    }
}
