(define null (lambda (e) (eq e nil)))

(null nil)

(null ())

(null t)

(null (quote a))

(null ((lambda (x) x) ()))
