pipeline {
  agent {
    node {
      label 'osx'
    }
    
  }
  stages {
    stage('Build') {
      steps {
        parallel(
          "Build": {
            sh 'echo build'
            
          },
          "build win": {
            echo 'build for windows'
            node(label: 'vg-host')
            
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
      }
    }
  }
  environment {
    ENV_VAR1 = '123'
  }
}