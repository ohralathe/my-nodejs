@Library('my-pipeline-lib') _

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
        branch 'prod'
      }
      steps {
        sh 'echo Deploy'
      }
    }
  }
  environment {
    ENV_VAR1 = '123'
  }
}