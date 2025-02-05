import axios, { AxiosError, HttpStatusCode } from 'axios';
import toast from 'react-hot-toast';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

const handleAxiosError = (error: AxiosError) => {
  if (error.response?.status === HttpStatusCode.BadRequest) {
    toast.error('Your request is invalid. Please try again.');
  } else if (error.response?.status === HttpStatusCode.InternalServerError) {
    toast.error('Something went very wrong!');
  }
};

export const post = async (endpoint: string, data: object) => {
  try {
    const response = await api.post(endpoint, data);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      handleAxiosError(error);
    } else {
      console.error(error);
      toast.error('Something went unexpectedly wrong!');
    }
  }
};

export const get = async (endpoint: string, params: object) => {
  try {
    const response = await api.get(endpoint, { params });
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      handleAxiosError(error);
    } else {
      console.error(error);
      toast.error('Something went unexpectedly wrong!');
    }
  }
};
