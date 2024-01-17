//
//  HTTPHeader+Extensions.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import UIKit
import Alamofire

fileprivate var releaseVersionNumber: String {
    Bundle.main.releaseVersionNumber ?? "1.0.0"
}

var defaultHeaderDictionary: [String: String] {
    [
        "x-device-id": UIDevice.current.deviceId,
        "app-version": releaseVersionNumber,
        "user-agent" : "GymTeam (com.lukakorica.GymTeam; v\(releaseVersionNumber)"
    ]
}

extension UIDevice {
    var deviceId: String {
        identifierForVendor?.uuidString.sha256() ?? ""
    }
}

extension Bundle {
    var releaseVersionNumber: String? {
        return infoDictionary?["CFBundleShortVersionString"] as? String
    }
    var buildVersionNumber: String? {
        return infoDictionary?["CFBundleVersion"] as? String
    }
}
