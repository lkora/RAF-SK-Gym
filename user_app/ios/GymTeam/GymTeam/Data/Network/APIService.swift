//
//  APIService.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

protocol SimpleAPIService {
    func perform(request: URLRequest, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol APIService: SimpleAPIService {
    func perform<T: Decodable>(request: APIRequest, completion: @escaping (Result<T, Error>) -> Void)
}
