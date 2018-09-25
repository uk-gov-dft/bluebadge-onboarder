package uk.org.dft_bluebadge;

import java.util.function.Predicate;

public class ArgValidator{
  public static <T> T validate(T arg, Predicate<T> evaluator, String message){
    System.out.println(arg);
    if (!evaluator.test(arg)) {
      throw new IllegalArgumentException(message);
    }
    return arg;
  }
}
