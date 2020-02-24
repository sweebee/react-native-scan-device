import { NativeModules, NativeEventEmitter } from 'react-native';

const { ScanDevice } = NativeModules;

/**
 * @callback ScanDeviceWrapper~scanBroadcastCallback
 * @param {Object} event (prop) {string} data
 */

class ScanDeviceWrapper
{
    /**
     * Subscribes to the Scanner's native broadcast event with a callback
     * 
     * @param {ScanDeviceWrapper~scanBroadcastCallback} callback 
     * 
     * @returns EmitterSubscription
     */
    onReceive(callback) {
        const eventEmitter = new NativeEventEmitter(NativeModules.ScanDevice);
        const subscription = eventEmitter.addListener('scan/received', callback);
        return subscription;
    }
}

export default new ScanDeviceWrapper();
