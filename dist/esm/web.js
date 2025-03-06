import { WebPlugin } from '@capacitor/core';
export class SocketTCPWeb extends WebPlugin {
    constructor() {
        super({
            name: 'SocketTCP',
            platforms: ['web']
        });
        this.NOT_SUPPORTED = "ERR_PLATFORM_NOT_SUPPORTED";
    }
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async connect(_options) {
        return new Promise((_resolve, reject) => {
            reject(this.NOT_SUPPORTED);
        });
    }
    async disconnect(_options) {
        return new Promise((_resolve, reject) => {
            reject(this.NOT_SUPPORTED);
        });
    }
    async send(_options) {
        return new Promise((_resolve, reject) => {
            reject(this.NOT_SUPPORTED);
        });
    }
    async sendRaw(_options) {
        return new Promise((_resolve, reject) => {
            reject(this.NOT_SUPPORTED);
        });
    }
}
//# sourceMappingURL=web.js.map