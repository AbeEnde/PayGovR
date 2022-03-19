import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPaygove, defaultValue } from 'app/shared/model/paygove.model';

const apiUrl = 'api/paygoves';
export const createEntity = createAsyncThunk(
    'paygove/create_entity',
    async (entity: IPaygove, thunkAPI) => {
      const result = await axios.post<IPaygove>(apiUrl, cleanEntity(entity));
    //  thunkAPI.dispatch(getEntities({}));
      return result;
    },
    { serializeError: serializeAxiosError }
  );

  export const getPayRedi = createAsyncThunk(
    'redirect',
    async () => {
      const requestUrl = `${apiUrl}`
     // const resultred = await axios.get<any>(apiUrl);
    //  thunkAPI.dispatch(getEntities({}));
    return axios.get<any>(requestUrl);
    },
    { serializeError: serializeAxiosError }
  );