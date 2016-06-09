import { NativeModules } from 'react-native';
let { RNPortAvailable } = NativeModules;

export default class PortAvailable {

  static available_ports = [];

  static check = (port) => {
    return new Promise((resolve, reject) => {
      RNPortAvailable.available(port).then((available) => {
        resolve(available);
      }).catch(err => {
        reject(err);
      })
    })
  }

  static checkRange = (min_port, max_port, stop = 0) => {
    return new Promise((resolve, reject) => {
      RNPortAvailable.range(min_port, max_port, stop).then((availablePorts) => {
        PortAvailable.available_ports = availablePorts;
        resolve(availablePorts);
      }).catch(err => {
        reject(err);
      })
    })
  }

  static getPorts = () => {
    return PortAvailable.available_ports;
  }

}
