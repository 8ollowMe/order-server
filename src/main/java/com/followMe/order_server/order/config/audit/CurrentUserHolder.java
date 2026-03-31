package com.followMe.order_server.order.config.audit;

public class CurrentUserHolder {
  private static final ThreadLocal<String> holder = new ThreadLocal<>();

  public static void set(String userId) {
    holder.set(userId);
  }

  public static String get() {
    return holder.get();
  }

  public static void clear() {
    holder.remove();
  }
}
