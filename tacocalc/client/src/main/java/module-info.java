module client {
  exports tacocalc.client;

  requires spring.boot;
  requires spring.web;
  requires java.net.http;
  requires transitive core;
  requires com.google.gson;
}
