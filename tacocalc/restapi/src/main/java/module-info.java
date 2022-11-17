module restapi {
  requires spring.boot;
  requires spring.web;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;
  requires spring.core;
  requires transitive core;
  requires data;
  requires transitive com.google.gson;

  opens tacocalc.restapi;
}
