import { NativeModules, NativeEventEmitter } from 'react-native';

const { ScanDevice } = NativeModules;

class SubscriptionWrapper
{
    constructor(subscription) {
        this.subscription = subscription;
    }

    offReceive() {
        this.subscription.remove();
    }
}

class ScanDeviceWrapper
{
    constructor() {console.log('constructed')}

    onReceive(callback) {
        const eventEmitter = new NativeEventEmitter(NativeModules.ScanDevice);
        const subscription = eventEmitter.addListener('scan/received', callback);
        return new SubscriptionWrapper(subscription);
    }
}

export default new ScanDeviceWrapper();
