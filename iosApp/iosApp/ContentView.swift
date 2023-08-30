import SwiftUI
import shared
import Foundation

struct ComposeView: UIViewControllerRepresentable {
    
    
    func makeUIViewController(context: Context) -> UIViewController {
        
        return Main_iosKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
            
    }
}

