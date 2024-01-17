//
//  GymAPIService.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

final class GymAPIService {
    
    private let apiService: APIService
    
    init(apiService: APIService) {
        self.apiService = apiService
    }
    
    
    // Auth
    func login(username: String, password: String, completion: @escaping ((Result<LoginResponse, Error>) -> Void)) {
        apiService.perform(request: LoginRequest(params: LoginParams(userName: username, password: password)), completion: completion)
    }
        
    func registerClient(params: RegisterClientParams, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: RegisterClientRequest(params: params), completion: completion)
    }
    
    func registerManager(params: RegisterManagerParams, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: RegisterManagerRequest(params: params), completion: completion)
    }
    
    // Profile
    func getProfile(completion: @escaping ((Result<User, Error>) -> Void)) {
        apiService.perform(request: GetProfileRequest(), completion: completion)
    }
    
    func editProfile(params: EditProfileParams, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: EditProfileRequest(params: params), completion: completion)
    }
    
    // Admin
    func banUser(with username: String, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: BanUserRequest(username: username), completion: completion)
    }
    
    func unbanUser(with username: String, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: UnbanUserRequest(username: username), completion: completion)
    }
    
    func getUsers(completion: @escaping ((Result<[User], Error>) -> Void)) {
        apiService.perform(request: UsersRequest(), completion: completion)
    }
    
    // Scheduling
    func getSchedule(completion: @escaping ((Result<[Appointment], Error>) -> Void)) {
        apiService.perform(request: GetScheduleRequest(), completion: completion)
    }
    
    func cancleAppointment(with id: Int, completion: @escaping ((Result<[GeneralResponse], Error>) -> Void)) {
        apiService.perform(request: CancelAppointmentRequest(params: CancelAppointmentParams(id: id)), completion: completion)
    }
    
    func makeAppointment(with appointmentId: Int, completion: @escaping ((Result<[GeneralResponse], Error>) -> Void)) {
        apiService.perform(request: MakeAppointmentRequest(params: MakeAppointmentParams(id: appointmentId)), completion: completion)
    }

    
    // Manager
    func editGym(with new: Gym, completion: @escaping ((Result<[GeneralResponse], Error>) -> Void)) {
        apiService.perform(request: EditGymRequest(params: EditGymParams(id: new.id, name: new.name, description: new.description, numberOfTrainers: new.numberOfTrainers)), completion: completion)
    }
    
    func addTraining(new training: Training, completion: @escaping ((Result<[GeneralResponse], Error>) -> Void)) {
        apiService.perform(request: AddTrainingRequest(new: training), completion: completion)
    }
    

}
