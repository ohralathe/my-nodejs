@Librar("pl-shared-libs") _

// Don't re-sent the error message to slack in failure block if another similar message already sent
boolean sendErrorMessage = false

pipeline {
  agent {
    node {
      label "master"
    }
  }

  parameters {
    booleanParam(name: "SKIP_DEPLOY_TO_TEST", defaultValue: false, description: "Skip build and deploy the branch master to Test server")
    booleanParam(name: "SKIP_DEPLOY_TO_STAGING_AS_TEST", defaultValue: true, description: "Skip build and deploy the branch master to Staging server")
    booleanParam(name: "SKIP_DEPLOY_TO_STAGING", defaultValue: false, description: "Skip build and deploy the branch prod to Staging server")
    booleanParam(name: "SKIP_DEPLOY_TO_PROD", defaultValue: true, description: "Skip build and deploy the branch prod to PRODUCTION server")
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
          return env.BRANCH_NAME == "master" && !params.SKIP_DEPLOY_TO_TEST
        }
      }

      environment {
        REMOTE_HOST = "${env.TEST_SERVER_HOST}"
        CREDENTIAL_ID = "ssh_credential_to_dev_server"
      }

      steps {
          acme.setName('Alice')
          echo acme.name /* prints: 'Alice' */
          acme.caution 'The queen is angry!'
      }
    }
  }
}


