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
          return env.BRANCH_NAME == "master"
        }
      }

      environment {
        REMOTE_HOST = "${env.TEST_SERVER_HOST}"
        CREDENTIAL_ID = "ssh_credential_to_dev_server"
      }

      steps {
        script {
          acme.setName('Alice')
          echo acme.name /* prints: 'Alice' */
          acme.caution 'The queen is angry!'
        }
      }
    }
  }
}


