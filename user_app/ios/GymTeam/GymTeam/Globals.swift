//
//  Globals.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation
import SwiftData

@Model
final class Globals {
    var baseServerUrl: String?
    
    init(baseServerUrl: String?) {
        self.baseServerUrl = baseServerUrl
    }
}
