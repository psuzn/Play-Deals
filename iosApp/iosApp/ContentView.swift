import SwiftUI
import shared
import Foundation

struct ComposeView: UIViewControllerRepresentable {
    
    var appearanceManager = AppearanceManager()
    
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = Main_iosKt.MainViewController(appearanceManager: appearanceManager)
        syncThemeWithUIViewController(controller)
        
        controller.view.backgroundColor = .green
        

        return controller
    }
    
    func syncThemeWithUIViewController(_ viewController:UIViewController){
        
        appearanceManager.appearanceMode.subscribe(block:{ (apperanceMode) in
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

