Object.defineProperty(exports,"__esModule",{value:true});var _reactNative=require('react-native');function _classCallCheck(instance,Constructor){if(!(instance instanceof Constructor)){throw new TypeError("Cannot call a class as a function");}}var 
RNPortAvailable=_reactNative.NativeModules.RNPortAvailable;var 

PortAvailable=function PortAvailable(){_classCallCheck(this,PortAvailable);};PortAvailable.

available_ports=[];PortAvailable.

check=function(port){
return new Promise(function(resolve,reject){
RNPortAvailable.available(port).then(function(available){
resolve(available);}).
catch(function(err){
reject(err);});});};PortAvailable.




checkRange=function(min_port,max_port){var stop=arguments.length<=2||arguments[2]===undefined?0:arguments[2];
return new Promise(function(resolve,reject){
RNPortAvailable.range(min_port,max_port,stop).then(function(availablePorts){
PortAvailable.available_ports=availablePorts;
resolve(availablePorts);}).
catch(function(err){
reject(err);});});};PortAvailable.




getPorts=function(){
return PortAvailable.available_ports;};exports.default=PortAvailable;
