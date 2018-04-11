package(default_visibility = ["//visibility:public"])

java_test(
  name = "game",
  size = "small",
  srcs = ["NimGameTest.java"],
  test_class = "com.games.nim.NimGameTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/nim:nim",
    "//third_party:junit4",
    "//third_party:mockito",
  ]
)

java_test(
  name = "action",
  size = "small",
  srcs = ["NimActionTest.java"],
  test_class = "com.games.nim.NimActionTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/nim:nim",
    "//third_party:junit4",
  ]
)

java_test(
  name = "state",
  size = "small",
  srcs = ["NimStateTest.java"],
  test_class = "com.games.nim.NimStateTest",
  deps = [
    "//src/main/java/com/games/general:general",
    "//src/main/java/com/games/nim:nim",
    "//third_party:junit4",
  ]
)
