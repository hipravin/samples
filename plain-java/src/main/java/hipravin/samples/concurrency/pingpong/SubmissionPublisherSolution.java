package hipravin.samples.concurrency.pingpong;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

//reactive streams are not applicable to ping pong task
public class SubmissionPublisherSolution {
    public static class EndSubscriber<T> implements Flow.Subscriber<T> {
        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            System.out.printf("%s, %s%n", item, Thread.currentThread().getName());
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            System.out.println("onComplete");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        EndSubscriber<String> subscriber2 = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber2);

        List<String> items = IntStream.range(0, 100)
                .mapToObj(String::valueOf)
                .toList();


        items.forEach(item -> {
            System.out.printf("%s, submit.retval -> %d%n", item, publisher.submit(item));
        });
        System.out.println("All submitted");
        publisher.close();

        Thread.sleep(1000);
    }
}
