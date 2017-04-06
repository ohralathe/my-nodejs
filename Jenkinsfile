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
    NPM_ACCESS_KEY = credentials("npm_access_token")
    GITHUB_CREDENTIAL_ID = "private_github_repo_credential"
  }

  stages {
    stage("Install node modules") {
      steps {
        installNodeModules()
      }
    }

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
        script {
          if (!buildAndDeploy([environment: "test", disabledDeploy: env.BRANCH_NAME.startsWith("PR-")])) {
            sendErrorMessage = true
          }
        }
      }
    }
  }


}

// TODO: these below method will be considered to move to shared library
void installNodeModules() {
  // YARN needs .npmrc to download the private packages from npmjs.com
  writeFile(encoding: "UTF-8", file: ".npmrc", text: "//registry.npmjs.org/:_authToken=${env.NPM_ACCESS_KEY}")
//  sh "yarn install"
}


boolean buildAndDeploy(Map inputMap) {
  boolean succeed = true
  try {

//    sh "yarn run build"

    if (!inputMap.disabledDeploy) {
      deploy()
    }

    currentBuild.result = "SUCCESS"
  } catch (ex) {
    currentBuild.result = "FAILURE"
    succeed = false
  }

  return succeed
}

def packageBuild() {
  sh '''
    homeDir=`pwd`
    
    cp package.json .npmrc build
    cp yarn.lock build
    
    # Compress the build
    cd $homeDir
    tar -zcvf hub-build.tgz build/
  '''
}

/**
 * Send a file from local to remote host
 */
void sendFile(String srcFile, String target, String remoteUser, String remoteHost, String credentialId) {
  sshagent(credentials: [credentialId]) {
    // Increase the performance for scp
    // https://developer.rackspace.com/blog/speeding-up-ssh-session-creation/
    String cmd = "scp -q -C -o StrictHostKeyChecking=no -o ControlMaster=auto -o ControlPersist=60s ${srcFile} ${remoteUser}@${remoteHost}:${target}"
    sh(cmd)
  }
}

void extractBuild(String remoteUser, String remoteHost, String credentialId) {
  // Switch to remoteUser to interactive to SHELL on remote because sshAgent will use the user jenkins
  String prefixCmd = "ssh -q -C -o StrictHostKeyChecking=no -o ControlMaster=auto -o ControlPersist=60s ${remoteUser}@${remoteHost} sudo -H -S -n -u ${remoteUser} /bin/bash "
  String deployScriptFile = makeDeployScript()
  String targetFile = "/tmp/${deployScriptFile}"

  sendFile(deployScriptFile, targetFile, remoteUser, remoteHost, credentialId)

  try {
    execRemoteShell(credentialId, prefixCmd + targetFile)
  } finally {
    // Remove deploy script file
//    execRemoteShell(credentialId, "${prefixCmd} -c \"'rm -f ${targetFile}'\"")
  }
}

String makeDeployScript() {
  String script = '''
      set -e
      homeDir=`pwd`
      buildDir=$homeDir/hub-build
      appName=hub

      # KOBITON_* variables were defined in `ubuntu` user but somehow they're not available in remote shell
      # So we need to manually set them for "npm"
      # Note: pm2 however doesn't need this
      source ~/.profile
      
      echo "KOBITON_DB_NAME $KOBITON_DB_NAME"
      echo "KOBITON_DB_HOST $KOBITON_DB_HOST"
      
  '''

  String fileName = "deploy_script_${new Date().getTime()}.sh"
  writeFile(file: fileName, text: script)

  // Add execute permission for deployment script file
  sh "chmod +x ${fileName}"
  return fileName
}

void deploy() {
//  packageBuild()
//  sendFile("./hub-build.tgz", "/home/ubuntu/", env.REMOTE_USER, env.REMOTE_HOST, env.CREDENTIAL_ID)
  extractBuild(env.REMOTE_USER, env.REMOTE_HOST, env.CREDENTIAL_ID)
}

void execRemoteShell(String credentialId, String script) {
  sshagent(credentials: [credentialId]) {
    sh(script)
  }
}


