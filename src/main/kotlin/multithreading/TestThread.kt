package hello                      //  可选的包头

internal class RunnableDemo(private val threadName: String) : Runnable {
    private var t: Thread? = null

    init {
        println("Creating $threadName")
    }

    override fun run() {
        println("Running $threadName")
        try {
            for (i in 4 downTo 1) {
                println("Thread: $threadName, $i")
                // Let the thread sleep for a while.
                Thread.sleep(50)
            }
        } catch (e: InterruptedException) {
            println("Thread $threadName interrupted.")
        }
        println("Thread $threadName exiting.")
    }

    fun start() {
        println("Starting $threadName")
        if (t == null) {
            t = Thread(this, threadName)
            t!!.start()
        }
    }
}

object TestThread {
    @JvmStatic
    fun main(args: Array<String>) {
        val R1 = RunnableDemo("Thread-1")
        R1.start()
        val R2 = RunnableDemo("Thread-2")
        R2.start()
    }
}
//更多请阅读：https://www.yiibai.com/java_concurrency/concurrency_overview.html

