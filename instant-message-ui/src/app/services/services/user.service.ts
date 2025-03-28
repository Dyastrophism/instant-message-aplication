/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllUser } from '../fn/user/get-all-user';
import { GetAllUser$Params } from '../fn/user/get-all-user';
import { UserResponse } from '../models/user-response';

@Injectable({ providedIn: 'root' })
export class UserService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllUser()` */
  static readonly GetAllUserPath = '/api/v1/users';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllUser$Response(params?: GetAllUser$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<UserResponse>>> {
    return getAllUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllUser(params?: GetAllUser$Params, context?: HttpContext): Observable<Array<UserResponse>> {
    return this.getAllUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<UserResponse>>): Array<UserResponse> => r.body)
    );
  }

}
