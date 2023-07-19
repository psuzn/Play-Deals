import SwiftUI
import shared
import Foundation

struct ComposeView: UIViewControllerRepresentable {

    var appearanceModeManager = AppearanceModeManager()

    func makeUIViewController(context: Context) -> UIViewController {
        let controller = Main_iosKt.MainViewController(appearanceModeManager: appearanceModeManager)
        syncThemeWithUIViewController(controller)
        return controller
    }

    func syncThemeWithUIViewController(_ viewController:UIViewController){

        appearanceModeManager.appearanceMode.subscribe(block:{ (apperanceMode) in
            DispatchQueue.main.async {
                switch(apperanceMode!){
                case .black:
                    viewController.view.window?.overrideUserInterfaceStyle = .dark
                    break;
                case .dark:
                    viewController.view.window?.overrideUserInterfaceStyle = .dark
                    break;
                case .light:
                    viewController.view.window?.overrideUserInterfaceStyle = .light
                    break;
                case .system :
                    viewController.view.window?.overrideUserInterfaceStyle = .unspecified
                    break;
                default: break;
                }
            }
        })
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

