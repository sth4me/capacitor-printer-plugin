// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPrinterPlugin",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorPrinterPlugin",
            targets: ["PrinterPluginPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "PrinterPluginPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/PrinterPluginPlugin"),
        .testTarget(
            name: "PrinterPluginPluginTests",
            dependencies: ["PrinterPluginPlugin"],
            path: "ios/Tests/PrinterPluginPluginTests")
    ]
)