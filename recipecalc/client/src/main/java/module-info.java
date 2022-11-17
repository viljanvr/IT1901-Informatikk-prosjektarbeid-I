module client {
  exports recipecalc.client;

  requires java.net.http;
  requires transitive core;
  requires com.google.gson;
}
