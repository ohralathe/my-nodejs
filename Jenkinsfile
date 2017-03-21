pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh 'echo build'
      }
    }
    stage('Test') {
      steps {
        sh 'echo check'
//        junit 'reports/**/*.xml'
      }
    }
    stage('Deploy') {
      steps {
        sh 'echo Deploy'
      }
    }
  }
}