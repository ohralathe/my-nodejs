@Library('my-pipeline-lib')
import libs.*
def base = new Base([ctx: this, orgName: 'ohralathe'])

String str = "aaaaaa"

pipeline {
  agent {
    node {
      label 'vg-host'
    }
  }

  stages {
    stage('Init') {
      steps {
        sh "echo `git config --get remote.origin.url`"
      }
    }

    stage('Pre-build') {
      when {
        expression {
          return env.BRANCH_NAME == "master" || env.BRANCH_NAME.startsWith("PR-")
        }
      }
      steps {
        echo "Prebuild blabal dfd ddcc"
        script {
          boolean ret = deploy([disabledDeploy: env.BRANCH_NAME.startsWith("PR-")])
          env.ENV_VAR = "333333"
        }
      }
    }

    stage('Build') {
      steps {
        parallel(
            "Build": {
              sh 'echo build'

            },
            "build win": {
              echo 'build for windows'
            },
            "build from shared lib": {
              script{
                base.build()
                echo "stageErrors: ${base.stageErrors}"
                base.deploy()
              }
            }
        )
      }

    }


    stage('Test') {
      steps {
        parallel(
            "Test": {
              sh 'echo check'

            },
            "Test on win": {
              echo 'test on windowd'

            }
        )
      }
    }

    stage('Deploy') {

      steps {
        sh 'echo Deploy'

        script {
          String message = "ablabalbal abla"
          echo message
        }
      }
    }
  }
  environment {
    ENV_VAR1 = '123'
  }

  post {
    always {
      script {

        echo "ENV_VAR1: ${env.ENV_VAR1}"
        echo "stageErrors: ${base.stageErrors}"
        if (base.stageErrors) {
          echo "DDDD: ${base.stageErrors}"
        }
      }
    }
  }
}

boolean deploy(data) {
  sh "echo builddf dfdccccccc"
  if (!data.disabledDeploy) {
    sh "echo deployment blblbll"
  }
  return true
}