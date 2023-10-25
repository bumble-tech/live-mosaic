# Bumble Tech Live Mosaic Challenges

## Do you want to win a prize?

The following are a set of exciting challenges for you to tackle. They’re designed to test your
skills, foster innovation, and offer attractive prizes. Go ahead, participate, and win!

## Jetpack Compose challenges

1. **C01 – Migrate** [`composed`](https://developer.android.com/reference/kotlin/androidx/compose/ui/package-summary#\(androidx.compose.ui.Modifier\).composed\(kotlin.Function1,kotlin.Function1\))
 **modifiers to** [`Modifier.Node`](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.Node)

   * *Description*: [The Jetpack Compose team is migrating](https://youtu.be/BjGX2RftXsU?feature=shared\&t=920) 
     `composed` Modifiers to `Modifier.Node` to increase the performance of Compose apps. Find 
     `composed` Modifiers in the project, and convert them to `Modifier.Node`.
   * *Example in the Compose codebase*: [PR of the Modifier.background migration](https://android-review.googlesource.com/c/platform/frameworks/support/+/2440999).

2. **C02 – Transition between nodes using other shapes**

   * *Description*: Take a look at how the current transition effect has been implemented in 
     `DottedMeshShape`. This class utilises circles, but it would be nice to allow also other shapes: 
     for instance hexagons. Extend the class to support at least circles and hexagons.

## Performance challenges

1. **P01 – Measure the performance of the app**

   * *Description*: We want a way to inspect and monitor the performance of Puzzyx. 
     [Setup macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview#project-setup) 
     in the project and [add tests](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview#create-macrobenchmark)
     to the project that check its startup time.

2. **P02 – Add baseline profiles to Puzzyx**

   * *Description*: We want to improve the code execution of Puzzyx by shipping baselines profiles 
     when the app is run. [Create baseline profiles](https://developer.android.com/topic/performance/baselineprofiles/create-baselineprofile)
     for the app and [benchmark](https://developer.android.com/topic/performance/baselineprofiles/create-baselineprofile#benchmark-baseline-profile) 
     the performance improvements.

## Build & CI challenges

1. **B01 – Add Detekt and Compose rules**

   * *Description*: Integrate [Detekt](https://detekt.dev) and [Compose Rules](https://mrmans0n.github.io/compose-rules/)
     into the project, enhancing source code quality. Your challenge involves embedding Detekt for
     static analysis, incorporating Compose Rules for Jetpack Compose issue detection, and refining
     the existing codebase. Furthermore, you'll adapt Github Actions to include these verification
     processes in the current build workflow.
