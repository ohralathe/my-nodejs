@Library("my-pipeline-lib@master") _

// Don't re-sent the error message to slack in failure block if another similar message already sent
boolean sendErrorMessage = false

pipeline {
  agent {
    node {
      label "master"
    }
  }

  options {
    disableConcurrentBuilds()
  }

  environment {
    REMOTE_USER = "ubuntu"
  }

  stages {
    // For Test environment
    stage("Deploy to Test") {
      when {
        expression {
          // Allow to skip deploy the branch master to Test server in case manual
          return env.BRANCH_NAME.startsWith("PR-")
        }
      }

      steps {
        script {
          vars.setErrorMessage("Pelase update packaeg.json file")
          utils.addCommentToPR()
        }
      }
    }
  }
}


