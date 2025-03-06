import Foundation
import Capacitor
import SwiftSocket

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(SocketTCP)
public class SocketTCP: CAPPlugin {
    
    var clients: [TCPClient] = []
    
    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": value
        ])
    }
    
    @objc func connect(_ call: CAPPluginCall) {
        guard let ip = call.getString("ipAddress") else {
            call.reject("Must provide ip address to connect")
            return
        }
        let port = call.options["port"] as? Int32 ?? 9100
        
        let client = TCPClient(address: ip, port: port)
        switch client.connect(timeout: 10) {
          case .success:
            clients.append(client);
            call.resolve([
                "success": true,
                "client": clients.count - 1
            ])
          case .failure(let error):
            call.reject(error.localizedDescription)
        }
    }
    
    @objc func send(_ call: CAPPluginCall) {
        let clientIndex = call.getInt("client") ?? -1
        if (clientIndex == -1)    {
            call.reject("No client specified")
        }
        let client = clients[clientIndex]
        let dataStr = call.getString("data") ?? ""
        let dataAr: Array  = dataStr.split(separator: ",").map(String.init)
        var dataAr8 = [UInt8]()
        
        for b in dataAr {
            dataAr8.append(UInt8(b)!)
        }
        
        switch client.send(data: dataAr8) {
          case .success:
            call.resolve()
          case .failure(let error):
            call.reject(error.localizedDescription)
        }
    }
    
    @objc func sendRaw(_ call: CAPPluginCall) {
        let clientIndex = call.getInt("client") ?? -1
        if (clientIndex == -1)    {
            call.reject("No client specified")
        }
        let client = clients[clientIndex]
        let data = call.getString("data") ?? ""
        
        var byteArray = [Byte]()
        for char in data.utf8{
            byteArray += [char]
        }
        
        switch client.send(data: byteArray) {
          case .success:
            call.resolve()
          case .failure(let error):
            call.reject(error.localizedDescription)
        }
    }
    
    @objc func disconnect(_ call: CAPPluginCall) {
        let clientIndex = call.getInt("client") ?? -1
        if (clientIndex == -1)  {
            call.reject("No client specified")
        }
        if (clients.indices.contains(clientIndex))   {
            clients[clientIndex].close()
        }
        call.resolve([
            "success": true,
            "client": clientIndex
        ])
    }
}
