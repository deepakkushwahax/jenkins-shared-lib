def call(Map config = [:]) {
    // Configuration values with defaults or errors
    def repoUrl   = config.repoUrl ?: error("Missing required parameter: repoUrl")
    def branch    = config.branch ?: "main"
    def mavenCmd  = config.mavenCmd ?: "package"
    def notifyVia = config.notifyVia ?: "console"

    def status = "SUCCESS"

    try {
        stage("Clone Repository") {
            echo "Cloning ${repoUrl} [branch: ${branch}]"
            git branch: branch, url: repoUrl
        }

        stage("Run Maven Command") {
            echo "Executing: mvn ${mavenCmd}"
            sh "mvn ${mavenCmd}"
        }
    } catch (err) {
        status = "FAILURE"
        echo "Error during build: ${err.message}"
        throw err
    } finally {
        stage("Send Notification") {
            def message = """
🔔 *Build Notification* 🔔
*Status:* ${status}
*Repo:* ${repoUrl}
*Branch:* ${branch}
*Command:* mvn ${mavenCmd}
""".stripIndent()

            if (notifyVia == "console") {
                echo message
            } else if (notifyVia == "slack") {
                slackSend(color: status == "SUCCESS" ? 'good' : 'danger', message: message)
            } else if (notifyVia == "telegram") {
                // Use shell command to send Telegram message via Bot API
                def token = "<your_bot_token>"
                def chatId = "<your_chat_id>"
                sh """
                    curl -s -X POST https://api.telegram.org/bot${token}/sendMessage \\
                        -d chat_id=${chatId} \\
                        -d text="${message.replaceAll('"', '\\"')}"
                """
            } else {
                echo "Unknown notification method: ${notifyVia}"
            }
        }
    }
}
