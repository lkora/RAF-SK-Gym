//
//  AppDelegate.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        return true
    }
    
    
}


extension AppDelegate: ShouldLogoutServiceDelegate {
    func shouldLogout(with error: Error) {
        debugPrint("Logout with: \(error.localizedDescription)")
    }
}
