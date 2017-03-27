@Library('my-pipeline-lib')
import libs.*
def base = new Base([ctx: this, orgName: 'ohralathe'])

node('master') {
  def extraEnv
  withCredentials([string(credentialsId: 'NPM_ACCESS_TOKEN', variable: 'AN_ACCESS')]) {
    extraEnv = env.AN_ACCESS
    sh '''
      echo  $AN_ACCESS
    '''
    if (env.AN_ACCESS == "AN_ACCESS") {
      sh 'echo ddddddddd'
    }
  }

  def path = "PATH+EXTRA=${extraEnv}"
  withEnv([path]) {
    stage("test") {
      println path
      sh "printenv"
      if (env.AN_ACCESS == "AN_ACCESS") {
        sh 'echo ddddddDDDDDddd'
      }

      if (env.AN_ACCESS != "AN_ACCESS") {
        sh 'echo dddddddCCCCCCdd'
      }
      if (extraEnv == "AN_ACCESS") {
        sh 'echo extraEnvccccc'
      }

      if (extraEnv != "AN_ACCESS") {
        sh 'echo extraEnvCDDDDDDccccc'
      }
    }
  }

}
//
//pipeline {
//  agent {
//    node {
//      label 'vg-host'
//    }
//
//  }
//
//
//  stages {
//    stage('init') {
//      environment {
//        AN_ACCESS_KEY = credentials('job-test-password')
//        NEW_DOMAIN_KEY = credentials('job-multibranch-test')
//      }
//      steps {
//        sh 'echo "DDDDDD"'
//        sh 'printenv'
//        echo '-----'
//        echo "$AN_ACCESS_KEY"
//        echo env.AN_ACCESS_KEY
//        echo "$NEW_DOMAIN_KEY"
//        echo env.NEW_DOMAIN_KEY
//        sh 'echo "DDDDDD"'
//        script {
//          if (env.NEW_DOMAIN_KEY == '123456') {
//            echo 'new domain'
//          } else {
//            echo 'not new dmain'
//          }
//        }
//
//      }
//    }
//
//    stage('Build') {
//      steps {
//        parallel(
//            "Build": {
//              sh 'echo build'
//
//            },
//            "build win": {
//              echo 'build for windows'
//            },
//            "build from shared lib": {
//              script{
//                base.build()
//              }
//            }
//        )
//      }
//
//    }
//
//
//    stage('Test') {
//      steps {
//        parallel(
//          "Test": {
//            sh 'echo check'
//
//          },
//          "Test on win": {
//            echo 'test on windowd'
//
//          }
//        )
//      }
//    }
//
//    stage('Deploy') {
//
//      steps {
//        sh 'echo Deploy'
//
//        script {
//          String message = "ablabalbal abla"
//          echo message
//        }
//      }
//    }
//  }
//  environment {
//    ENV_VAR1 = '123'
//  }
//}