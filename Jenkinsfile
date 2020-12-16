#!groovy

/* Only keep the 5 most recent builds. */
def projectProperties = [

		//只保留5个构建记录
		[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '5']],

		//参数化构建
		parameters([
				choice(name: 'action', choices: 'NoAction\nPublishToMaven\nPublishDocs', description: '操作类型'),
				choice(name: 'env', choices: 'dev\ntesting\nstaging\nproduction', description: '发布环境')
		])
]

properties(projectProperties)

env.MAVEN_REPO = "http://192.168.11.230:8081"

def docsHost = '192.168.11.231'

//分支黑名单（分支名出现在白名单中将不会被checkout）
def branchBlacklist = []

//分支白名单（分支名或分支名的前缀（以'-'分割）出现在白名单中才会被checkout）
def branchWhitelist = ['master', 'jiac', 'release', 'test', 'liufeng']

//启用黑名单或白名单开关：-1表示启用黑名单；1表示启用白名单；0表示都不启用
def blackOrWhitelist = 0


node {

	if (params.action != "NoAction" && canGetBranch(blackOrWhitelist, branchBlacklist, branchWhitelist)) {

		echo "黑名单：${branchBlacklist}"
		echo "白名单：${branchWhitelist}"
		echo "当前启用：${blackOrWhitelist == 0 ? '不启用' : blackOrWhitelist == 1 ? '白名单' : '黑名单'}"
		echo "用户选择的操作为：${params.action}"
		echo "用户选择的环境为：${params.env}"

		env.ENV = params.env

		stage('Checkout Source Code') {
			checkout scm
		}

		stage('Build') {
			try {
				sh "chmod +x gradlew"
				sh "./gradlew clean build -x test"
			} catch (err) {
				stage('Faliure Notification') {
					def to = emailextrecipients([
							[$class: 'DevelopersRecipientProvider']
					])

					mail to: to, subject: "${env.JOB_NAME} Failed!",
							body: "${env.JOB_NAME} failed the last time it was run. See ${env.BUILD_URL} for more information."
					currentBuild.result = 'FAILURE'
				}
			}
		}

		stage('Publish') {
			try {
				def url = sh(script: "./gradlew --info  publish | grep -oP '.*zip'|awk \'END {print \$NF}\'", returnStdout: true,).trim()
				env.REPO_URL = env.MAVEN_REPO + "${url}"
				echo "${env.REPO_URL}"
				def packageStr = env.REPO_URL.split('/')[-1]
				echo "Publish to url: ${env.REPO_URL}"
				currentBuild.description = "Build Environment <br/> <a href=${env.REPO_URL}>${packageStr}</a>"
			} catch (err) {
				stage('Faliure Notification') {
					def to = emailextrecipients([
							[$class: 'DevelopersRecipientProvider']
					])

					mail to: to, subject: "${env.JOB_NAME} Failed!",
							body: "${env.JOB_NAME} failed the last time it was run. See ${env.BUILD_URL} for more information."
					currentBuild.result = 'FAILURE'
				}
			}
		}

		if (params.action == "PublishDocs") {
			stage('PublishDocs') {
				try {
					buildVersion = getBuildVersion()
					echo "${buildVersion}"
					echo "Deploy jcohy-framework-reference?"
					sh "ssh root@${docsHost} 'bash -x -s' < ./docs.sh ${buildVersion} ${env.REPO_URL}"
				} catch (err) {
					def user = err.getCauses()[0].getUser()
					if ('SYSTEM' == user.toString()) {
						echo "Input timeout"
					} else {
						echo "Aborted by: [${user}]"
					}
				}
			}
		}

	}
}

def canGetBranch(blackOrWhitelist, branchBlacklist, branchWhitelist) {
	if (blackOrWhitelist == 0) {
		res = true
	} else if (blackOrWhitelist == -1) {
		if (branchBlacklist.contains(env.BRANCH_NAME)) res = false
		else res = true
	} else if (blackOrWhitelist == 1) {
		if (branchWhitelist.contains(env.BRANCH_NAME) || branchWhitelist.contains(env.BRANCH_NAME.substring(0, env.BRANCH_NAME.indexOf('-')))) res = true
		else res = false
	} else {
		res = false
	}
	res
}

def getGitProperties() {
	git_properties = "'gradle.properties'"
	def content = readFile 'gradle.properties'
	Properties properties = new Properties()
	InputStream is = new ByteArrayInputStream(content.getBytes());
	properties.load(is)
	properties
}

def getBuildVersion() {
	buildVersion = getGitProperties()."VERSION"
	buildVersion
}

//def getMavenRepo() {
//	if (getBuildVersion().contains('SNAPSHOT')) {
//		MAVEN_REPO = "http://192.168.11.230:8081/repository/snapshot/"
//	} else {
//		MAVEN_REPO = "http://192.168.11.230:8081/repository/releases/"
//	}
//	MAVEN_REPO
//}