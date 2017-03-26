@Library('my-pipeline-lib')
import libs.*


pipeline {
  agent {
    node {
      label 'vg-host'
    }
    
  }
  stages {
    stage('Build') {
      when {
        branch 'master'
      }
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
                new Base([ctx: this, orgName: 'ohralathe']).build()
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
      when {
        branch 'test-1'
      }

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
}