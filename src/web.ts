import { WebPlugin } from '@capacitor/core';
import { SocketTCPPlugin } from './definitions';

export class SocketTCPWeb extends WebPlugin implements SocketTCPPlugin {
  
  NOT_SUPPORTED: string = "ERR_PLATFORM_NOT_SUPPORTED";

  constructor() {
    super();
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }

  async connect(_options: { ipAddress: string, port: number }): Promise<any> {
    return new Promise<void>((_resolve, reject) => {
      reject(this.NOT_SUPPORTED);
    });
  }

  async disconnect(_options: { client: number }): Promise<any> {
    return new Promise<void>((_resolve, reject) => {
      reject(this.NOT_SUPPORTED);
    });
  }

  async send(_options: { data: string }): Promise<any> {
    return new Promise<void>((_resolve, reject) => {
      reject(this.NOT_SUPPORTED);
    });
  }

  async sendRaw(_options: { data: string }): Promise<any> {
    return new Promise<void>((_resolve, reject) => {
      reject(this.NOT_SUPPORTED);
    });
  }
}
