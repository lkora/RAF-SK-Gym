//
//  GymTeamApp.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import SwiftUI
import SwiftData

@main
struct GymTeamApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            SplashView()
        }
    }
}
