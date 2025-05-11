def call(Map config = [:]) {
    def repoUrl   = config.repoUrl ?: error("Missing parameter: repoUrl")
    def branch    = config.branch ?: "main"
    def mavenCmd  = config.mavenCmd ?: "clean package"

    try {
        stage("Clone Maven Project") {
            echo "📦 Cloning repository: ${repoUrl} (branch: ${branch})"
            git branch: branch, url: repoUrl
        }

        stage("Run Maven Command") {
            echo "⚙️ Running: mvn ${mavenCmd}"
            sh "mvn ${mavenCmd}"
        }
    } catch (err) {
        echo "❌ Build failed: ${err.message}"
        throw err
    }
}
