//
//  ContentView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import SwiftUI
import SwiftData

struct ContentView: View {
    @State var loggedIn: Bool = false
    
    var body: some View {
        NavigationStack {
            if !loggedIn {
                LoginView()
            } else {
                
            }
        }
    }

}

#Preview {
    ContentView()
}
