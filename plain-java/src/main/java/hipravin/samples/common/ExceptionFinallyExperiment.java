package hipravin.samples.common;

public class ExceptionFinallyExperiment {
    public static void main(String[] args) throws InterruptedException {
        AutoCloseable c1 = () -> {
            System.out.println("close 1");
            throw new IllegalStateException("Something went wrong 1");
        };
        AutoCloseable c2 = () -> {
            System.out.println("close 2");
            throw new IllegalStateException("Something went wrong 2");
        };
        AutoCloseable c3 = null;
        try (c1;c2;c3) {
            System.out.println("Start");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Finish");
//            throw new RuntimeException("Something went wrong in finally");
        }

    }
}
/*
Start
close 2
close 1
Finish
Exception in thread "main" java.lang.RuntimeException: java.lang.IllegalStateException: Something went wrong 2
	at hipravin.samples.common.ExceptionFinallyExperiment.main(ExceptionFinallyExperiment.java:17)
Caused by: java.lang.IllegalStateException: Something went wrong 2
	at hipravin.samples.common.ExceptionFinallyExperiment.lambda$main$1(ExceptionFinallyExperiment.java:11)
	at hipravin.samples.common.ExceptionFinallyExperiment.main(ExceptionFinallyExperiment.java:16)
	Suppressed: java.lang.IllegalStateException: Something went wrong 1
		at hipravin.samples.common.ExceptionFinallyExperiment.lambda$main$0(ExceptionFinallyExperiment.java:7)
		at hipravin.samples.common.ExceptionFinallyExperiment.main(ExceptionFinallyExperiment.java:14)
 */