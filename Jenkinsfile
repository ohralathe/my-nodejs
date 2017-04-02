@Library('my-pipeline-lib')
import libs.*
def base = new Base([ctx: this, orgName: 'ohralathe'])

String str = "aaaaaa"

pipeline {
  agent {
    node {
      label 'master'
    }
  }

  stages {
    stage('Init') {
      steps {
        script {
          sh "echo 'GIT_COMMIT: ${scm.GIT_COMMIT}'"
          sh "echo 'GIT_BRANCH: ${scm.GIT_BRANCH}'"
          sh "git config --get remote.origin.url"
        }
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
          if (ret)
            str = "cddjodjfosd jfasjf"
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
                echo "stageErrors: ${String.valueOf(base.stageErrors)}"
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
        echo "str: ${str}"
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