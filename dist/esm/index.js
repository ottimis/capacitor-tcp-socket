import { registerPlugin } from '@capacitor/core';
const SocketTCP = registerPlugin('SocketTCP', {
    ios: () => import('./web').then(m => new m.SocketTCPWeb()),
});
export * from './definitions';
export { SocketTCP };
//# sourceMappingURL=index.js.map