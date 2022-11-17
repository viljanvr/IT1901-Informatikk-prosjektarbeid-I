module client {
  exports tacocalc.client;

  requires java.net.http;
  requires transitive core;
  requires com.google.gson;
}
